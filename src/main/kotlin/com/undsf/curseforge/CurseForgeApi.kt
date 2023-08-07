package com.undsf.curseforge

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import com.undsf.curseforge.schemas.*
import org.slf4j.LoggerFactory
import java.net.URI
import java.net.URLEncoder
import java.net.http.HttpClient
import java.net.http.HttpRequest
import java.net.http.HttpRequest.BodyPublisher
import java.net.http.HttpResponse.BodyHandlers
import java.nio.charset.StandardCharsets

private val logger = LoggerFactory.getLogger(CurseForgeApi::class.java)

class QueryParamList : ArrayList<Pair<String, String>>() {
    fun add(name: String, value: String): Boolean {
        return add(Pair(name, value))
    }

    override fun toString(): String {
        return joinToString("&") {
            val name = URLEncoder.encode(it.first, StandardCharsets.UTF_8)
            val value = URLEncoder.encode(it.second, StandardCharsets.UTF_8)
            "$name=$value"
        }
    }
}

class CurseForgeApi(
    private val baseURL: String = "https://api.curseforge.com",
    private var apiKey: String? = null
) {
    private val client = HttpClient.newHttpClient()
    private val mapper = jacksonObjectMapper()

    init {
        if (apiKey == null) {
            apiKey = System.getenv("CURSE_FORGE_API_KEY")
            logger.debug("未指定API_KEY，从环境变量CURSE_FORGE_API_KEY读取：${apiKey}")
        }
    }

    private fun sendGetRequest(uri: String, query: String? = null): String? {
        val url = StringBuilder(baseURL)
        url.append(uri)
        if (query != null) {
            url.append("?")
            url.append(query)
        }

        val req = HttpRequest.newBuilder()
            .GET()
            .uri(URI.create(url.toString()))
            .header("x-api-key", apiKey)
            .build()

        val resp = client.send(req, BodyHandlers.ofString())
        if (resp.statusCode() != 200) {
            return null
        }
        return resp.body()
    }

    private fun sendGetRequest(uri: String, params: QueryParamList = QueryParamList()): String? {
        return sendGetRequest(uri, params.toString())
    }

    private fun sendPostRequest(uri: String, body: Any): String? {
        val url = "${baseURL}${uri}"
        val reqJson = mapper.writeValueAsString(body)
        val req = HttpRequest.newBuilder()
            .POST(HttpRequest.BodyPublishers.ofString(reqJson))
            .uri(URI.create(url))
            .build()
        val resp = client.send(req, BodyHandlers.ofString())
        if (resp.statusCode() != 200) {
            return null
        }
        return resp.body()
    }

    // region Mods
    fun searchMod(
        gameId: Int = GameIdMinecraft,
        classId: Int? = ClassIdMod,
        categoryId: Int? = null,
        categoryIds: List<Int>? = null,
        gameVersion: String? = null,
        searchFilter: String? = null,
        sortField: ModsSearchSortField? = ModsSearchSortField.Popularity,
        sortOrder: String? = "desc",
        modLoaderType: ModLoaderType? = null,
        modLoaderTypes: List<ModLoaderType>? = null,
        gameVersionTypeId: Int? = null,
        authorId: Int? = null,
        primaryAuthorId: Int? = null,
        slug: String? = null,
        index: Int? = null,
        pageSize: Int? = null
    ): PaginationResponse<Mod>? {
        val uri = "/v1/mods/search"
        val params = QueryParamList()

        // required
        params.add("gameId", "$gameId")

        // 主分类
        if (classId != null) {
            params.add("classId", "$classId")
        }

        // 子分类
        // categoryIds 优先读取
        if (categoryIds != null) {
            val value = categoryIds.joinToString(",", "[", "]") {
                "$it"
            }
            params.add("categoryIds", value)
        }
        // categoryId
        else if (categoryId != null) {
            params.add("categoryId", "$categoryId")
        }

        // Minecraft版本
        if (gameVersion != null) {
            params.add("gameVersion", gameVersion)
        }

        // 关键字
        if (searchFilter != null) {
            params.add("searchFilter", searchFilter)
        }

        // 排序规则
        if (sortField != null) {
            params.add("sortField", "${sortField.value}")
        }
        if (sortOrder != null) {
            params.add("sortOrder", sortOrder)
        }

        // 模组加载器类型
        // modLoaderTypes 优先读取
        if (modLoaderTypes != null) {
            modLoaderTypes.joinToString(",", "[", "]") {
                it.name
            }
        }
        else if (modLoaderType != null) {
            params.add("modLoaderType", "${modLoaderType.value}")
        }

        if (slug != null) {
            params.add("slug", slug)
        }

        var limitValue = 50 // 默认 50
        if (pageSize != null) {
            limitValue = pageSize
            if (pageSize > 50) {
                // 最大值 50
                limitValue = 50
            }
            // TODO 检查下限
            params.add("pageSize", "$limitValue")
        }

        var endIndex = limitValue
        if (index != null) {
            endIndex = index + limitValue
            if (endIndex > 10000) {
                logger.warn("index设置为${index}将会超过上限")
            }
            params.add("index", "$index")
        }

        val respJson = sendGetRequest(uri, params) ?: return null
        return mapper.readValue(respJson)
    }

    fun getMod(): DataResponse<Mod>? {
        return null
    }

    // POST
    fun getMods(): DataResponse<List<Mod>>? {
        return null
    }
    // endregion

    // region Files
    fun getModFile(): DataResponse<ModFile>? {
        return null
    }

    fun getModFiles(
        modId: Int,
        gameVersion: String? = null,
        modLoaderType: ModLoaderType? = null,
        gameVersionTypeId: Int? = null,
        index: Int? = null,
        pageSize: Int? = null
    ): PaginationResponse<ModFile>? {
        val uri = "/v1/mods/${modId}/files"
        val params = QueryParamList()

        if (gameVersion != null) {
            params.add("gameVersion", gameVersion)
        }

        if (modLoaderType != null) {
            params.add("modLoaderType", "${modLoaderType.value}")
        }

        if (gameVersionTypeId != null) {
            params.add("gameVersionTypeId", "$gameVersionTypeId")
        }

        var limitValue = 50 // 默认 50
        if (pageSize != null) {
            limitValue = pageSize
            if (pageSize > 50) {
                // 最大值 50
                limitValue = 50
            }
            // TODO 检查下限
            params.add("pageSize", "$limitValue")
        }

        var endIndex = limitValue
        if (index != null) {
            endIndex = index + limitValue
            if (endIndex > 10000) {
                logger.warn("index设置为${index}将会超过上限")
            }
            params.add("index", "$index")
        }

        val respJson = sendGetRequest(uri, params) ?: return null
        return mapper.readValue(respJson)
    }

    // POST
    fun getFiles(): DataResponse<List<Mod>>? {
        return null
    }
    // endregion


    companion object {
        const val GameIdMinecraft = 432
        const val ClassIdMod = 6
    }
}
package com.undsf.curseforge

import com.undsf.curseforge.schemas.ModLoaderType
import org.junit.jupiter.api.Test
import org.slf4j.LoggerFactory

private val logger = LoggerFactory.getLogger(CurseForgeApiTests::class.java)

class CurseForgeApiTests {
    val client = CurseForgeApi()

    @Test
    fun testSearchMods() {
        val results_jei = client.searchMod(
            slug = "jei"
        )
        logger.info("找到slug为jei的mod ${results_jei!!.pagination.totalCount}个")
    }

    @Test
    fun testGetModFiles() {
        val results_jei = client.getModFiles(
            238222,
            gameVersion = "1.20.1",
            modLoaderType = ModLoaderType.Forge,
        )
        logger.info("找到1.20.1-Forge的jei的文件 ${results_jei!!.pagination.totalCount}个")
    }
}
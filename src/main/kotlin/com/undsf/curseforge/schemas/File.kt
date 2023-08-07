package com.undsf.curseforge.schemas

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class File(
    val id: Int,
    val gameId: Int,
    val modId: Int,
    val displayName: String,
    val fileName: String,
    // val releaseType: ReleaseType,
    val hashes: List<FileHash>,
    val fileDate: String,
    val fileLength: Long,
    val downloadCount: Long,
    val downloadUrl: String,
    val gameVersions: List<String>,
    val dependencies: List<Dependency>,
) {
    // region 额外属性
    val sha1: String? get() {
        val hash = hashes.find { it.algo == FileHash.Sha1 }
        return hash?.value
    }

    val requiredDependencies: List<Dependency> get() {
        return dependencies.filter {
            it.relationType == Dependency.RequiredDependency
        }
    }
    // endregion

    // region 方法
    override fun toString(): String {
        return "$modId/$id $sha1 $downloadUrl"
    }
    // endregion

    // region 内部类
    class FileHash(
        val value: String,
        val algo: Int
    ) {
        companion object {
            const val Sha1 = 1
            const val Md5 = 2
        }
    }

    class Dependency(
        val modId: Int,
        val relationType: Int,
    ) {
        companion object {
            const val EmbeddedLibrary = 1
            const val OptionalDependency = 2
            const val RequiredDependency = 3
            const val Tool = 4
            const val Incompatible = 5
            const val Include = 6
        }
    }
    // endregion
}
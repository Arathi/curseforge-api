package com.undsf.curseforge.schemas

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

typealias ModFile = File

@JsonIgnoreProperties(ignoreUnknown = true)
class Mod(
    val id: Int,
    val gameId: Int,
    val name: String,
    val slug: String,
    val links: ModLinks,
    val summary: String,
    val downloadCount: Long,
    val isFeatured: Boolean,
    val primaryCategoryId: Int,
    val categories: List<Category>,
    val classId: Int?,
    val authors: List<ModAuthor>,
    val logo: ModAsset,
    val screenshots: List<ModAsset>,
    val mainFileId: Int,
    val latestFiles: List<ModFile>,
    val dateCreated: String,
    val dateModified: String,
    val dateReleased: String,
    val allowModDistribution: Boolean?,
    val gamePopularityRank: Int,
    val isAvailable: Boolean,
    val thumbsUpCount: Int,
) {
    class ModLinks(
        val websiteUrl: String,
        val wikiUrl: String,
        val issuesUrl: String,
        val sourceUrl: String,
    )

    class ModAuthor(
        val id: Int,
        val name: String,
        val url: String,
    )

    class ModAsset(
        val id: Int,
        val modId: Int,
        val title: String,
        val description: String,
        val thumbnailUrl: String,
        val url: String,
    )
}
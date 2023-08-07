package com.undsf.curseforge.schemas

import com.fasterxml.jackson.annotation.JsonIgnoreProperties

@JsonIgnoreProperties(ignoreUnknown = true)
class Category(
    /**
     * The category id
     */
    val id: Int,

    /**
     * The game id related to the category
     */
    val gameId: Int,

    /**
     * Category name
     */
    val name: String,

    /**
     * The category slug as it appear in the URL
     */
    val slug: String,

    /**
     * The category URL
     */
    val url: String,

    /**
     * URL for the category icon
     */
    val iconUrl: String,

    /**
     * Last modified date of the category
     *
     * ISO8601 yyyy-MM-ddTHH:mm:ssZ
     */
    val dateModified: String,

    /**
     * A top level category for other categories
     */
    val isClass: Boolean?,

    /**
     * The class id of the category, meaning - the class of which this category is under
     */
    val classId: Int?,

    /**
     * The parent category for this category
     */
    val parentCategoryId: Int?,

    /**
     * The display index for this category
     */
    val displayIndex: Int?,
) {
}
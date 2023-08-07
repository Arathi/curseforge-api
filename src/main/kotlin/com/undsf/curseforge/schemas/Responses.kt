package com.undsf.curseforge.schemas

class Pagination(
    val index: Int,
    val pageSize: Int,
    val resultCount: Int,
    val totalCount: Long
)

class PaginationResponse<T>(
    val data: List<T>,
    val pagination: Pagination
)

class DataResponse<T>(
    val data: List<T>
)

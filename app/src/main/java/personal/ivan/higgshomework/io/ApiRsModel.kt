package personal.ivan.higgshomework.io

import com.squareup.moshi.Json

/**
 * Required GitHub user information
 */
data class GitHubUserSummary(
    @field:Json(name = "avatar_url") val avatarUrl: String?,
    @field:Json(name = "login") val username: String?,
    @field:Json(name = "site_admin") val admin: Boolean?
)

/**
 * Detailed information of a certain user
 */
data class GitHubUserDetails(
    @field:Json(name = "avatar_url") val avatarUrl: String?,
    @field:Json(name = "login") val username: String?,
    @field:Json(name = "bio") val biography: String?,
    @field:Json(name = "site_admin") val admin: Boolean?,
    val location: String?,
    @field:Json(name = "blog") val blogUrl: String?
)
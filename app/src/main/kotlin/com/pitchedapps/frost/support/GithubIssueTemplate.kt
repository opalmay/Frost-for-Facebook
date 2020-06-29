/*
 * Copyright 2020 Allan Wang
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package com.pitchedapps.frost.support

import com.pitchedapps.frost.BuildConfig
import okhttp3.HttpUrl

data class GithubIssueTemplate(
    val slug: String = "AllanWang/Frost-for-Facebook",
    val title: String? = null,
    val labels: List<String> = emptyList(),
    val assignees: List<String> = emptyList(),
    val template: String? = null,
    val body: String? = null
) {
    fun url(): String = HttpUrl.Builder()
        .scheme("https")
        .host("github.com")
        .addPathSegments(slug)
        .addPathSegments("issues/new")
        .apply {
            if (title != null) addQueryParameter("title", title)
            if (labels.isNotEmpty()) addQueryParameter("labels", labels.joinToString(","))
            if (assignees.isNotEmpty()) addQueryParameter("assignees", assignees.joinToString(","))
            if (template != null) addQueryParameter("template", template)
            if (body != null) addQueryParameter("body", body)
        }
        .build()
        .toString()

    companion object
}

private fun baseIssue(): GithubIssueTemplate = GithubIssueTemplate(
    slug = "AllanWang/Frost-for-Facebook",
    assignees = listOf("AllanWang"),
    labels = listOf("bug")
)

private fun baseInfo(): String = "Version ${BuildConfig.VERSION_NAME}"

fun GithubIssueTemplate.Companion.imageError(exception: Exception): String = baseIssue().copy(
    title = "Image Error [${exception.message?.hashCode()}]: ${exception.javaClass.name}",
    body = "${exception.message}\n\n${baseInfo()}"
).url()

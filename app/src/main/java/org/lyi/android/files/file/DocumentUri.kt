/*
 * Copyright (c) 2025 lingyicute <li@92li.us.kg>
 * All Rights Reserved.
 */

package org.lyi.android.files.file

import android.net.Uri
import android.os.Parcelable
import android.provider.DocumentsContract
import kotlinx.parcelize.Parcelize
import kotlinx.parcelize.WriteWith
import org.lyi.android.files.app.contentResolver
import org.lyi.android.files.compat.DocumentsContractCompat
import org.lyi.android.files.util.StableUriParceler

@Parcelize
@JvmInline
value class DocumentUri(val value: @WriteWith<StableUriParceler> Uri) : Parcelable {
    val treeDocumentId: String
        get() = DocumentsContract.getTreeDocumentId(value)

    val documentId: String
        get() = DocumentsContract.getDocumentId(value)
}

fun Uri.asDocumentUriOrNull(): DocumentUri? =
    if (isDocumentUri) DocumentUri(this) else null

fun Uri.asDocumentUri(): DocumentUri {
    require(isDocumentUri)
    return DocumentUri(this)
}

private val Uri.isDocumentUri: Boolean
    get() = DocumentsContractCompat.isDocumentUri(this)

val DocumentUri.displayName: String?
    get() {
        try {
            contentResolver.query(
                value, arrayOf(DocumentsContract.Document.COLUMN_DISPLAY_NAME), null, null, null
            ).use { cursor ->
                if (cursor != null && cursor.moveToFirst()) {
                    val displayNameIndex = cursor.getColumnIndex(
                        DocumentsContract.Document.COLUMN_DISPLAY_NAME
                    )
                    if (displayNameIndex != -1) {
                        val displayName = cursor.getString(displayNameIndex)
                        if (!displayName.isNullOrEmpty()) {
                            return displayName
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return null
    }

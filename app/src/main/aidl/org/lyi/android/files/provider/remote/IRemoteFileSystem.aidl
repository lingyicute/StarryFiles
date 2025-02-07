package org.lyi.android.files.provider.remote;

import org.lyi.android.files.provider.remote.ParcelableException;

interface IRemoteFileSystem {
    void close(out ParcelableException exception);
}

package org.lyi.android.files.provider.remote;

import org.lyi.android.files.provider.remote.ParcelableException;
import org.lyi.android.files.util.RemoteCallback;

interface IRemotePathObservable {
    void addObserver(in RemoteCallback observer);

    void close(out ParcelableException exception);
}

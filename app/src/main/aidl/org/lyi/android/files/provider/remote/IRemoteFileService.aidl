package org.lyi.android.files.provider.remote;

import org.lyi.android.files.provider.remote.IRemoteFileSystem;
import org.lyi.android.files.provider.remote.IRemoteFileSystemProvider;
import org.lyi.android.files.provider.remote.IRemotePosixFileAttributeView;
import org.lyi.android.files.provider.remote.IRemotePosixFileStore;
import org.lyi.android.files.provider.remote.ParcelableObject;

interface IRemoteFileService {
    IRemoteFileSystemProvider getRemoteFileSystemProviderInterface(String scheme);

    IRemoteFileSystem getRemoteFileSystemInterface(in ParcelableObject fileSystem);

    IRemotePosixFileStore getRemotePosixFileStoreInterface(in ParcelableObject fileStore);

    IRemotePosixFileAttributeView getRemotePosixFileAttributeViewInterface(
        in ParcelableObject attributeView
    );
}

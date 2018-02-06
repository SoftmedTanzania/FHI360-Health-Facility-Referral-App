package com.softmed.htmr_facility.dom.dao;

import android.arch.lifecycle.ComputableLiveData;
import android.arch.lifecycle.LiveData;
import android.arch.persistence.db.SupportSQLiteStatement;
import android.arch.persistence.room.EntityDeletionOrUpdateAdapter;
import android.arch.persistence.room.EntityInsertionAdapter;
import android.arch.persistence.room.InvalidationTracker.Observer;
import android.arch.persistence.room.RoomDatabase;
import android.arch.persistence.room.RoomSQLiteQuery;
import android.database.Cursor;
import android.support.annotation.NonNull;
import com.softmed.htmr_facility.dom.objects.PostOffice;
import java.lang.Override;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import javax.annotation.Generated;

@Generated("android.arch.persistence.room.RoomProcessor")
public class PostOfficeModelDao_Impl implements PostOfficeModelDao {
  private final RoomDatabase __db;

  private final EntityInsertionAdapter __insertionAdapterOfPostOffice;

  private final EntityDeletionOrUpdateAdapter __deletionAdapterOfPostOffice;

  public PostOfficeModelDao_Impl(RoomDatabase __db) {
    this.__db = __db;
    this.__insertionAdapterOfPostOffice = new EntityInsertionAdapter<PostOffice>(__db) {
      @Override
      public String createQuery() {
        return "INSERT OR REPLACE INTO `PostOffice`(`post_id`,`post_data_type`,`syncStatus`) VALUES (?,?,?)";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PostOffice value) {
        if (value.getPost_id() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getPost_id());
        }
        if (value.getPost_data_type() == null) {
          stmt.bindNull(2);
        } else {
          stmt.bindString(2, value.getPost_data_type());
        }
        stmt.bindLong(3, value.getSyncStatus());
      }
    };
    this.__deletionAdapterOfPostOffice = new EntityDeletionOrUpdateAdapter<PostOffice>(__db) {
      @Override
      public String createQuery() {
        return "DELETE FROM `PostOffice` WHERE `post_id` = ?";
      }

      @Override
      public void bind(SupportSQLiteStatement stmt, PostOffice value) {
        if (value.getPost_id() == null) {
          stmt.bindNull(1);
        } else {
          stmt.bindString(1, value.getPost_id());
        }
      }
    };
  }

  @Override
  public void addPostEntry(PostOffice postOffice) {
    __db.beginTransaction();
    try {
      __insertionAdapterOfPostOffice.insert(postOffice);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public void deletePostData(PostOffice postOffice) {
    __db.beginTransaction();
    try {
      __deletionAdapterOfPostOffice.handle(postOffice);
      __db.setTransactionSuccessful();
    } finally {
      __db.endTransaction();
    }
  }

  @Override
  public LiveData<List<PostOffice>> getPostOfficeEntries() {
    final String _sql = "select * from PostOffice";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<PostOffice>>() {
      private Observer _observer;

      @Override
      protected List<PostOffice> compute() {
        if (_observer == null) {
          _observer = new Observer("PostOffice") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfPostId = _cursor.getColumnIndexOrThrow("post_id");
          final int _cursorIndexOfPostDataType = _cursor.getColumnIndexOrThrow("post_data_type");
          final int _cursorIndexOfSyncStatus = _cursor.getColumnIndexOrThrow("syncStatus");
          final List<PostOffice> _result = new ArrayList<PostOffice>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PostOffice _item;
            _item = new PostOffice();
            final String _tmpPost_id;
            _tmpPost_id = _cursor.getString(_cursorIndexOfPostId);
            _item.setPost_id(_tmpPost_id);
            final String _tmpPost_data_type;
            _tmpPost_data_type = _cursor.getString(_cursorIndexOfPostDataType);
            _item.setPost_data_type(_tmpPost_data_type);
            final int _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getInt(_cursorIndexOfSyncStatus);
            _item.setSyncStatus(_tmpSyncStatus);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public int getUnpostedDataCount() {
    final String _sql = "select COUNT(*) from PostOffice where syncStatus = 0 ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public int getPostedDataCount() {
    final String _sql = "select COUNT(*) from PostOffice where syncStatus = 1 ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _result;
      if(_cursor.moveToFirst()) {
        _result = _cursor.getInt(0);
      } else {
        _result = 0;
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public List<PostOffice> getUnpostedData() {
    final String _sql = "select * from PostOffice where syncStatus = 0 ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    final Cursor _cursor = __db.query(_statement);
    try {
      final int _cursorIndexOfPostId = _cursor.getColumnIndexOrThrow("post_id");
      final int _cursorIndexOfPostDataType = _cursor.getColumnIndexOrThrow("post_data_type");
      final int _cursorIndexOfSyncStatus = _cursor.getColumnIndexOrThrow("syncStatus");
      final List<PostOffice> _result = new ArrayList<PostOffice>(_cursor.getCount());
      while(_cursor.moveToNext()) {
        final PostOffice _item;
        _item = new PostOffice();
        final String _tmpPost_id;
        _tmpPost_id = _cursor.getString(_cursorIndexOfPostId);
        _item.setPost_id(_tmpPost_id);
        final String _tmpPost_data_type;
        _tmpPost_data_type = _cursor.getString(_cursorIndexOfPostDataType);
        _item.setPost_data_type(_tmpPost_data_type);
        final int _tmpSyncStatus;
        _tmpSyncStatus = _cursor.getInt(_cursorIndexOfSyncStatus);
        _item.setSyncStatus(_tmpSyncStatus);
        _result.add(_item);
      }
      return _result;
    } finally {
      _cursor.close();
      _statement.release();
    }
  }

  @Override
  public LiveData<List<PostOffice>> getUnpostedLiveData() {
    final String _sql = "select * from PostOffice where syncStatus = 0 ";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<PostOffice>>() {
      private Observer _observer;

      @Override
      protected List<PostOffice> compute() {
        if (_observer == null) {
          _observer = new Observer("PostOffice") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfPostId = _cursor.getColumnIndexOrThrow("post_id");
          final int _cursorIndexOfPostDataType = _cursor.getColumnIndexOrThrow("post_data_type");
          final int _cursorIndexOfSyncStatus = _cursor.getColumnIndexOrThrow("syncStatus");
          final List<PostOffice> _result = new ArrayList<PostOffice>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PostOffice _item;
            _item = new PostOffice();
            final String _tmpPost_id;
            _tmpPost_id = _cursor.getString(_cursorIndexOfPostId);
            _item.setPost_id(_tmpPost_id);
            final String _tmpPost_data_type;
            _tmpPost_data_type = _cursor.getString(_cursorIndexOfPostDataType);
            _item.setPost_data_type(_tmpPost_data_type);
            final int _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getInt(_cursorIndexOfSyncStatus);
            _item.setSyncStatus(_tmpSyncStatus);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }

  @Override
  public LiveData<List<PostOffice>> getPostedData() {
    final String _sql = "select * from PostOffice where syncStatus = 1";
    final RoomSQLiteQuery _statement = RoomSQLiteQuery.acquire(_sql, 0);
    return new ComputableLiveData<List<PostOffice>>() {
      private Observer _observer;

      @Override
      protected List<PostOffice> compute() {
        if (_observer == null) {
          _observer = new Observer("PostOffice") {
            @Override
            public void onInvalidated(@NonNull Set<String> tables) {
              invalidate();
            }
          };
          __db.getInvalidationTracker().addWeakObserver(_observer);
        }
        final Cursor _cursor = __db.query(_statement);
        try {
          final int _cursorIndexOfPostId = _cursor.getColumnIndexOrThrow("post_id");
          final int _cursorIndexOfPostDataType = _cursor.getColumnIndexOrThrow("post_data_type");
          final int _cursorIndexOfSyncStatus = _cursor.getColumnIndexOrThrow("syncStatus");
          final List<PostOffice> _result = new ArrayList<PostOffice>(_cursor.getCount());
          while(_cursor.moveToNext()) {
            final PostOffice _item;
            _item = new PostOffice();
            final String _tmpPost_id;
            _tmpPost_id = _cursor.getString(_cursorIndexOfPostId);
            _item.setPost_id(_tmpPost_id);
            final String _tmpPost_data_type;
            _tmpPost_data_type = _cursor.getString(_cursorIndexOfPostDataType);
            _item.setPost_data_type(_tmpPost_data_type);
            final int _tmpSyncStatus;
            _tmpSyncStatus = _cursor.getInt(_cursorIndexOfSyncStatus);
            _item.setSyncStatus(_tmpSyncStatus);
            _result.add(_item);
          }
          return _result;
        } finally {
          _cursor.close();
        }
      }

      @Override
      protected void finalize() {
        _statement.release();
      }
    }.getLiveData();
  }
}

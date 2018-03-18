package com.grant.todo.TodoPackage;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

/**
 * Created by Grant on 3/12/18.
 */

public class TodoItem extends CheckableEditableItem implements Parcelable{

    public TodoItem(String text, long time) {
        this.text = text;
        this.time = time;
    }

    private TodoItem(Parcel in) {
        text = in.readString();
        time = in.readLong();
        isChecked = in.readByte() != 0;
        editClicked = in.readByte() != 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(text);
        dest.writeLong(time);
        dest.writeByte((byte) (isChecked ? 1 : 0));
        dest.writeByte((byte) (editClicked ? 1 : 0));
    }

    @Override
    public int describeContents() {
        return this.hashCode();
    }

    public static final Creator<TodoItem> CREATOR = new Creator<TodoItem>() {
        @Override
        public TodoItem createFromParcel(Parcel in) {
            return new TodoItem(in);
        }

        @Override
        public TodoItem[] newArray(int size) {
            return new TodoItem[size];
        }
    };
}

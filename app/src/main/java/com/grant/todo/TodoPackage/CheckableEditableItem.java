package com.grant.todo.TodoPackage;

/**
 * Created by Grant on 3/12/18.
 */

public class CheckableEditableItem {
    private String parent;
    protected String text;
    protected long time;
    protected boolean isChecked = false;
    protected boolean editClicked = false;

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public boolean requiresClock() {
        return time != 0;
    }

    public boolean isEditClicked() {
        return editClicked;
    }

    public void setEditClicked(boolean editClicked) {
        this.editClicked = editClicked;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }


    @Override
    public String toString(){
        StringBuilder output = new StringBuilder();
        output.append(text).append(",");
        output.append(String.valueOf(time)).append(",");
        output.append(String.valueOf(isChecked)).append(",");
        output.append(String.valueOf(editClicked)).append(",");
        return output.toString();
    }
}

package com.ncube.memberservice.domain;

import java.util.Objects;

public class FileVO {

    private String data;

    private String format;

    private String name;

    public FileVO() {

    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FileVO fileVO = (FileVO) o;
        return Objects.equals(data, fileVO.data) &&
                Objects.equals(format, fileVO.format) &&
                Objects.equals(name, fileVO.name);
    }

    @Override
    public int hashCode() {
        return Objects.hash(data, format, name);
    }

    @Override
    public String toString() {
        return "FileVO{" +
                "format='" + format + '\'' +
                ", name='" + name + '\'' +
                '}';
    }
}

package com.ncube.memberservice.domain;

import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.Objects;

public class MemberVO {

    private String id;

    @ApiModelProperty(required = true)
    private String firstName;

    @ApiModelProperty(required = true)
    private String lastName;

    @ApiModelProperty(required = true)
    private Date birthDate;

    private String postalCode;

    @ApiModelProperty(required = true)
    private Boolean active;

    private FileVO image;


    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Date getBirthDate() {
        return birthDate;
    }

    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
    }

    public String getPostalCode() {
        return postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public FileVO getImage() {
        return image;
    }

    public void setImage(FileVO image) {
        this.image = image;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        MemberVO memberVO = (MemberVO) o;
        return Objects.equals(id, memberVO.id) &&
                Objects.equals(firstName, memberVO.firstName) &&
                Objects.equals(lastName, memberVO.lastName) &&
                Objects.equals(birthDate, memberVO.birthDate) &&
                Objects.equals(postalCode, memberVO.postalCode) &&
                Objects.equals(active, memberVO.active) &&
                Objects.equals(image, memberVO.image);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, firstName, lastName, birthDate, postalCode, active, image);
    }

    @Override
    public String toString() {
        return "MemberVO{" +
                "id='" + id + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", birthDate=" + birthDate +
                ", postalCode='" + postalCode + '\'' +
                ", active=" + active +
                ", image=" + image +
                '}';
    }
}

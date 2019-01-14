package com.ncube.memberservice.domain;

import org.bson.types.Binary;
import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.Objects;

@Document(collection = "Members")
public class Member {

    @Id
    private ObjectId _id;

    private String firstName;

    private String lastName;

    private Date birthDate;

    private String postalCode;

    private Boolean active;

    private Binary image;

    private String imageName;

    private String imageFormat;

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
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

    public Binary getImage() {
        return image;
    }

    public void setImage(Binary image) {
        this.image = image;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    public String getImageFormat() {
        return imageFormat;
    }

    public void setImageFormat(String imageFormat) {
        this.imageFormat = imageFormat;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Member member = (Member) o;
        return Objects.equals(_id, member._id) &&
                Objects.equals(firstName, member.firstName) &&
                Objects.equals(lastName, member.lastName) &&
                Objects.equals(birthDate, member.birthDate) &&
                Objects.equals(postalCode, member.postalCode) &&
                Objects.equals(active, member.active) &&
                Objects.equals(image, member.image) &&
                Objects.equals(imageName, member.imageName) &&
                Objects.equals(imageFormat, member.imageFormat);
    }

    @Override
    public int hashCode() {
        return Objects.hash(_id, firstName, lastName, birthDate, postalCode, active, image, imageName, imageFormat);
    }
}

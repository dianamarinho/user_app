package com.api.user.dto;

import com.api.user.domain.User;

import java.util.Objects;

/**
 * The {@link User} data transfer object
 */
public class UserDto {

    private Integer id;
    private String name;
    private String addressName;
    private String zipCode;
    private String city;

    /**
     * Gets the id of the User DTO
     *
     * @return the User DTO id
     */
    public Integer getId() {
        return id;
    }

    /**
     * Sets the id of the User DTO
     *
     * @param id id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * Gets the name of the User DTO
     *
     * @return the User DTO name
     */
    public String getName() {
        return name;
    }

    /**
     * Sets the name of the User DTO
     *
     * @param name name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Gets the address name of the User DTO
     *
     * @return the User DTO address name
     */
    public String getAddressName() {
        return addressName;
    }

    /**
     * Sets the address name of the User DTO
     *
     * @param addressName address name to set
     */
    public void setAddressName(String addressName) {
        this.addressName = addressName;
    }

    /**
     * Gets the zip code of the User DTO
     *
     * @return the User DTO zip code
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * Sets the zip code of the User DTO
     *
     * @param zipCode zip code to set
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * Gets the city of the User DTO
     *
     * @return the User DTO city
     */
    public String getCity() {
        return city;
    }

    /**
     * Sets the city of the User DTO
     *
     * @param city city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @see Object#toString()
     * @return the string
     */
    @Override
    public String toString() {
        return "PoiDto{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", addressName='" + addressName + '\'' +
                ", zipCode='" + zipCode + '\'' +
                ", city='" + city + '\'' +
                '}';
    }

    /**
     * @see Object#equals(Object)
     *
     * @param o the object to compare
     * @return true or false
     */
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        UserDto poiDto = (UserDto) o;
        return Objects.equals(id, poiDto.id) && Objects.equals(name, poiDto.name) && Objects.equals(addressName, poiDto.addressName) && Objects.equals(zipCode, poiDto.zipCode) && Objects.equals(city, poiDto.city);
    }

    /**
     * @see Object#hashCode()
     *
     * @return int
     */
    @Override
    public int hashCode() {
        return Objects.hash(id, name, addressName, zipCode, city);
    }
}

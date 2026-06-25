package com.miniproject.absenhr.model;

import jakarta.persistence.*;

@Entity
@Table (name ="employees")
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column (name = "employee_code", length = 64, unique = true, nullable = false)
    private String employeeCode;

    @Column (name = "full_name", length = 64, nullable = false)
    private String fullName;

    @Column (name = "address", length = 124, nullable = false)
    private String address;

    @Column (name = "email", length = 256, unique = true, nullable = false)
    private String email;

    @Column (name = "phone",length = 18,unique = true, nullable = false)
    private String phone;

    @OneToOne()
    @JoinColumn (name = "user_id" , nullable = true)
    private Users user;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getEmployeeCode() {
        return employeeCode;
    }

    public void setEmployeeCode(String employeeCode) {
        this.employeeCode = employeeCode;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public Users getUser() {
        return user;
    }

    public void setUser(Users user) {
        this.user = user;
    }
}

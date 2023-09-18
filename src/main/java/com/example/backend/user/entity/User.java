package com.example.backend.user.entity;

import com.example.backend.BaseEntity;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Table(name = "USER")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class User extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "USER_ID")
    private Long id;

    @Column(nullable = false, length = 50, unique = true)
    private String email;

    @Column(nullable = false, length = 10)
    private String userName;

    @Column(nullable = false, length = 200)
    private String password;

    @Column(nullable = false, length = 15)
    private String mobilePhone;

    @Column(nullable = false, length = 15)
    private String companyPhone;

    @Embedded
    private Profile profile;

    @Column(nullable = false)
    @Enumerated(value = EnumType.STRING)
    private UserRole userRole;

    @Builder
    public User(String email, String userName, String password, Profile profile, UserRole userRole, boolean isSeller) {
        this.email =email;
        this.userName = userName;
        this.password = password;
        this.profile = profile;
        this.userRole = userRole;
    }

    public void changeAuthoritySellerToCustomer() {
        this.profile = Profile.createWithoutIntroduce()
                .nickName(this.profile.getNickName())
                .profileImage(this.getProfile().getProfileImage())
                .build();
        this.userRole = UserRole.USER;
    }

    public void changeAuthorityCustomerToSeller(String introduce) {
        this.profile = Profile.createWithIntroduce()
                .nickName(this.profile.getNickName())
                .profileImage(this.profile.getProfileImage())
                .introduce(introduce)
                .build();
        this.userRole = UserRole.SELLER;

    }

    public void modifyProfile(String nickName, String image) {
        this.profile = Profile.createWithoutIntroduce()
                .nickName(nickName)
                .profileImage(image)
                .build();
    }

    public void updatePassword(String password) {
        this.password = password;
    }

}
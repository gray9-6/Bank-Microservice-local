package com.eazybytes.accounts.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity//This Annotation is used to define a class as a table in the database.Means this class is a table in the database.
@Table(name = "accounts")
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Accounts extends BaseEntity {

    @Id
    @Column(name = "account_number")
    private Long accountNumber;

    @Column(name = "customer_id")
    private Long customerId;

    @Column(name = "account_type")
    private String accountType;

    @Column(name = "branch_address")
    private String branchAddress;

}

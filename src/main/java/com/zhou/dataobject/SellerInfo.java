package com.zhou.dataobject;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * @program sell
 * @description:
 * @author: 周茜
 * @create: 2019/11/28 13:16
 */
@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class SellerInfo {

    @Id
    private String sellerId;

    private String username;

    private String password;

    private String openid;
}

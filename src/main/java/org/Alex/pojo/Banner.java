package org.Alex.pojo;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.Hibernate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;

@Getter
@Setter
@ToString
@RequiredArgsConstructor
@Entity
@Table(name = "blog_banner")
public class Banner  implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id; //主键


    private String title; //标题

    private String link; //跳转链接

    private String cover; //图片

    private Integer ordered; //顺序

    private String summary; //描述信息


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || Hibernate.getClass(this) != Hibernate.getClass(o)) return false;
        Banner banner = (Banner) o;
        return id != null && Objects.equals(id, banner.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }
}

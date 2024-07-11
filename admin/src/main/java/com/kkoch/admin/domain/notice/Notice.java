package com.kkoch.admin.domain.notice;

import com.kkoch.admin.domain.BaseEntity;
import com.kkoch.admin.domain.admin.Admin;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Notice extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "notice_id")
    private Integer id;

    @Column(nullable = false, length = 50)
    private String title;

    @Lob
    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @Builder
    private Notice(boolean isDeleted, int createdBy, int lastModifiedBy, String title, String content) {
        super(isDeleted, createdBy, lastModifiedBy);
        this.title = title;
        this.content = content;
    }

    public static Notice of(boolean isDeleted, int createdBy, int lastModifiedBy, String title, String content) {
        return new Notice(isDeleted, createdBy, lastModifiedBy, title, content);
    }

    public static Notice create(String title, String content, Admin admin) {
        return of(false, admin.getId(), admin.getId(), title, content);
    }

    //== 비즈니스 로직 ==//
    public void edit(String title, String content, Admin admin) {
        super.modify(admin.getId());
        this.title = title;
        this.content = content;
    }

    public void remove(Admin admin) {
        super.modify(admin.getId());
        super.remove();
    }
}

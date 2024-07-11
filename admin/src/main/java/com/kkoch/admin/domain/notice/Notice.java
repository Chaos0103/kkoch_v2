package com.kkoch.admin.domain.notice;

import com.kkoch.admin.domain.BaseEntity;
import com.kkoch.admin.domain.TimeBaseEntity;
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
    private Long id;

    @Column(nullable = false, length = 50)
    private String title;

    @Lob
    @Column(nullable = false, columnDefinition = "text")
    private String content;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "admin_id")
    private Admin admin;

    @Builder
    private Notice(boolean isDeleted, Integer createdBy, Integer lastModifiedBy, String title, String content, Admin admin) {
        super(isDeleted, createdBy, lastModifiedBy);
        this.title = title;
        this.content = content;
        this.admin = admin;
    }

    public static Notice of(boolean isDeleted, Integer createdBy, Integer lastModifiedBy, String title, String content, Admin admin) {
        return new Notice(isDeleted, createdBy, lastModifiedBy, title, content, admin);
    }

    public static Notice create(String title, String content, Admin admin) {
        return of(false, admin.getId(), admin.getId(), title, content, admin);
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

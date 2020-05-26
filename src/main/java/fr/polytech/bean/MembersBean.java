package fr.polytech.bean;

import fr.polytech.constants.Constants;
import fr.polytech.dao.Page;
import fr.polytech.dao.member.MemberDao;
import fr.polytech.model.Member;
import lombok.Getter;

import javax.annotation.PostConstruct;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;

@Named("members")
@ViewScoped
public class MembersBean implements Serializable {

    @Getter
    private Page<Member> currentPage;

    @Inject
    private MemberDao memberDao;

    @PostConstruct
    private void load() {
        changeCurrentPage(1);
    }

    public void changeCurrentPage(int pageNumber) {
        if (pageNumber <= 0) {
            throw new IllegalArgumentException(Constants.Errors.NEGATIVE_PAGE_NUMBER);
        }
        currentPage = memberDao.getMemberPage(pageNumber);
    }
}

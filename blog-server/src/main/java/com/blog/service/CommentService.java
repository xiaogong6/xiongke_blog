package com.blog.service;


import com.api.dto.base.PageResultDTO;
import com.api.dto.comment.CommentAdminDTO;
import com.api.dto.comment.CommentDTO;
import com.api.dto.other.ReplyDTO;
import com.api.vo.other.CommentVO;
import com.api.vo.other.ConditionVO;
import com.api.vo.other.ReviewVO;
import com.baomidou.mybatisplus.extension.service.IService;
import com.core.modle.entity.Comment;

import java.util.List;

public interface CommentService extends IService<Comment> {

    void saveComment(CommentVO commentVO);

    PageResultDTO<CommentDTO> listComments(CommentVO commentVO);

    List<ReplyDTO> listRepliesByCommentId(Integer commentId);

    List<CommentDTO> listTopSixComments();

    PageResultDTO<CommentAdminDTO> listCommentsAdmin(ConditionVO conditionVO);

    void updateCommentsReview(ReviewVO reviewVO);

}

package com.ansj.service

import com.ansj.domain.Issue
import com.ansj.domain.IssueRepository
import com.ansj.model.IssueRequest
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import com.ansj.model.IssueResponse

@Service
class IssueService(
    private val issueRepository: IssueRepository
) {
    @Transactional
    fun create(userId: Long, request: IssueRequest): IssueResponse {
        val issue = Issue(
            summary = request.summary,
            description = request.description,
            userId = userId,
            type = request.type,
            priority = request.priority,
            status = request.status,
        )
        return IssueResponse(issueRepository.save(issue))
    }
}
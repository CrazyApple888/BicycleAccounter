package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.IssueDto
import ru.nsu.fit.domain.model.Issue
import javax.inject.Inject

class IssueMapper @Inject constructor() : Mapper<Issue, IssueDto> {
    override fun toDomain(item: IssueDto, options: Map<String, Int>): Issue {
        return Issue(
            issueId = item.issueId,
            description = item.description ?: ""
        )
    }

    override fun toData(item: Issue, options: Map<String, Int>): IssueDto {
        val cost by options
        return IssueDto(
            issueId = item.issueId,
            cost = cost,
            description = item.description
        )
    }
}
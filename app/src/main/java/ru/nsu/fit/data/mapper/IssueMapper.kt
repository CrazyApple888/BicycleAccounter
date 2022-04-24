package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.IssueDto
import ru.nsu.fit.domain.model.Issue
import javax.inject.Inject

class IssueMapper @Inject constructor() : Mapper<Issue, IssueDto> {
    override fun toDomain(item: IssueDto, options: Map<String, Int>): Issue {
        return Issue(
            issueId = item.issueId,
            description = item.description ?: "",
            cost = item.cost
        )
    }

    override fun toData(item: Issue, options: Map<String, Int>): IssueDto {
        return IssueDto(
            issueId = item.issueId,
            cost = item.cost,
            description = item.description
        )
    }
}
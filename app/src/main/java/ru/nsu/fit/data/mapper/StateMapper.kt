package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.BicycleStateDto
import ru.nsu.fit.domain.model.State
import javax.inject.Inject


class StateMapper @Inject constructor() : Mapper<State, BicycleStateDto> {
    override fun toDomain(item: BicycleStateDto, options: Map<String, Int>): State {
        return State(stateName = item.stateName)
    }

    override fun toData(item: State, options: Map<String, Int>): BicycleStateDto {
        val stateId by options
        return BicycleStateDto(
            stateId = stateId,
            stateName = item.stateName
        )
    }

}
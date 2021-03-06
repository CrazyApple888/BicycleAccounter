package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.BicycleStateDto
import ru.nsu.fit.data.model.StateDto
import ru.nsu.fit.domain.model.State
import javax.inject.Inject


class StateMapper @Inject constructor() : Mapper<State, BicycleStateDto> {
    override fun toDomain(item: BicycleStateDto, options: Map<String, Int>): State {
        return State(stateName = item.stateName.stateName)
    }

    override fun toData(item: State, options: Map<String, Int>): BicycleStateDto {
        val stateId by options.withDefault { 0 }
        return BicycleStateDto(
            stateId = stateId,
            stateName = StateDto.forName(item.stateName)
        )
    }

}
package ru.nsu.fit.data.mapper

import ru.nsu.fit.data.model.BicycleState
import ru.nsu.fit.domain.model.State
import javax.inject.Inject


class StateMapper @Inject constructor() : Mapper<State, BicycleState> {
    override fun toDomain(item: BicycleState, options: Map<String, Int>): State {
        return State(state = item.stateName)
    }

    override fun toData(item: State, options: Map<String, Int>): BicycleState {
        val stateId by options
        return BicycleState(
            stateId = stateId,
            stateName = item.state
        )
    }

}
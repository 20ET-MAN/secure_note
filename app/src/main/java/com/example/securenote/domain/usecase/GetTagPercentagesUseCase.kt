package com.example.securenote.domain.usecase

import com.example.securenote.domain.enum.NoteType
import com.example.securenote.domain.model.PieData
import com.example.securenote.domain.repository.NoteRepository
import jakarta.inject.Inject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class GetTagPercentagesUseCase @Inject constructor(private val noteRepository: NoteRepository) :
    BaseUseCase<BaseUseCase.NoParams, Flow<List<PieData>>>() {
    override suspend fun invoke(params: NoParams): Flow<List<PieData>> {
        return noteRepository.getPrevNotes().map { notes ->
            val grouped = notes.groupBy { block ->
                NoteType.getNoteTypeByValue(block.type.value)
            }

            grouped.map { (noteType, blocks) ->
                val percentage = (blocks.size.toFloat() / notes.size.toFloat()) * 100
                PieData(
                    value = percentage,
                    noteType = noteType
                )
            }
        }
    }
}
package com.epam.springproject.mapper;

import com.epam.springproject.dto.JournalDto;
import com.epam.springproject.model.Journal;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface JournalMapper {

    JournalMapper INSTANCE = Mappers.getMapper(JournalMapper.class);

    JournalDto mapJournalToJournalDto(Journal journal);

    Journal mapJournalDtoToJournal(JournalDto journalDto);

}

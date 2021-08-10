package com.github.googelfist.university.service;

import com.github.googelfist.university.entities.dto.TimetableDto;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.TestPropertySource;
import com.github.googelfist.university.entities.Group;
import com.github.googelfist.university.entities.Teacher;
import com.github.googelfist.university.entities.Timetable;
import com.github.googelfist.university.repository.TimetableRepository;

import javax.persistence.EntityNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static java.lang.String.format;
import static java.util.Collections.singletonList;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static com.github.googelfist.university.utils.Constants.*;

@SpringBootTest
@TestPropertySource(locations = "classpath:application-test.properties")
class TimetableServiceImplTest {
    @Mock
    private TimetableRepository mockTimetableRepository;
    private TimetableService timetableService;
    private Timetable timetable;
    private TimetableDto timetableDto;
    private List<Timetable> timetables;

    @BeforeEach
    void setUp() {
        Group group = new Group();
        Teacher teacher = new Teacher();

        teacher.setId(ID_1_VALUE);
        teacher.setFirstName(TEACHER_1_FIRST_NAME_VALUE);
        teacher.setLastName(TEACHER_1_LAST_NAME_VALUE);

        group.setId(ID_1_VALUE);
        group.setName(GROUP_1_NAME_VALUE);

        timetableService = new TimetableServiceImpl(mockTimetableRepository);

        timetable = new Timetable();
        timetable.setDate(DATE_1_VALUE);
        timetable.setStartTime(TIME_1_VALUE);
        timetable.setLectureHall(LECTURE_HALL_1_VALUE);
        timetable.setSubject(SUBJECT_1_VALUE);
        timetable.setTeacher(teacher);
        timetable.setGroup(group);

        timetableDto = new TimetableDto();
        timetableDto.setDate(DATE_1_VALUE);
        timetableDto.setStartTime(TIME_1_VALUE);
        timetableDto.setLectureHall(LECTURE_HALL_1_VALUE);
        timetableDto.setSubject(SUBJECT_1_VALUE);
        timetableDto.setGroupId(ID_1_VALUE);
        timetableDto.setGroupName(GROUP_1_NAME_VALUE);
        timetableDto.setTeacherId(ID_1_VALUE);
        timetableDto.setTeacherFirstName(TEACHER_1_FIRST_NAME_VALUE);
        timetableDto.setTeacherLastName(TEACHER_1_LAST_NAME_VALUE);

        timetables = singletonList(timetable);
    }

    @Test
    void shouldCallCreateTimetable() {
        timetableService.create(timetable);

        verify(mockTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(timetable);
    }

    @Test
    void shouldCallGetTimetableById() {
        when(mockTimetableRepository.findById(ID_1_VALUE)).thenReturn(Optional.ofNullable(timetable));

        Timetable actualTimetable = timetableService.getById(ID_1_VALUE);

        verify(mockTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findById(ID_1_VALUE);
        assertEquals(timetable, actualTimetable);
    }

    @Test
    void shouldCallGetAllTimetables() {
        when(mockTimetableRepository.findAll()).thenReturn(timetables);
        List<Timetable> actualTimetables = timetableService.getAll();

        verify(mockTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findAll();
        assertEquals(timetables, actualTimetables);
    }

    @Test
    void shouldCallUpdateTimetable() {
        timetableService.update(timetable);

        verify(mockTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(timetable);
    }

    @Test
    void shouldCallDeleteTimetable() {
        timetableService.delete(ID_1_VALUE);

        verify(mockTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).deleteById(ID_1_VALUE);
    }

    @Test
    void shouldCallGetAllDtoTimetablesPageable() {
        Pageable pageable = PageRequest.of(PAGE, SIZE);

        Page<Timetable> timetablesPage = new PageImpl<>(timetables, pageable, timetables.size());
        when(mockTimetableRepository.findAll(pageable)).thenReturn(timetablesPage);

        List<TimetableDto> timetablesDto = new ArrayList<>();
        timetablesDto.add(timetableDto);
        Page<TimetableDto> expectedTimetablesDtoPage = new PageImpl<>(timetablesDto, pageable, timetablesDto.size());

        Page<TimetableDto> actualTimetablesDtoPage = timetableService.getAllDto(pageable);

        verify(mockTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findAll(pageable);
        assertEquals(expectedTimetablesDtoPage, actualTimetablesDtoPage);
    }

    @Test
    void shouldCallGetDtoTimetableById() {
        when(mockTimetableRepository.findById(ID_1_VALUE)).thenReturn(Optional.ofNullable(timetable));
        TimetableDto actualTimetableDto = timetableService.getDtoById(ID_1_VALUE);
        verify(mockTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).findById(ID_1_VALUE);
        assertEquals(timetableDto, actualTimetableDto);
    }

    @Test
    void shouldCallCreateDtoTimetable() {
        timetableService.createDto(timetableDto);
        verify(mockTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(timetable);
    }

    @Test
    void shouldCallUpdateDtoTimetable() {
        timetableService.updateDto(timetableDto);
        verify(mockTimetableRepository, times(NUMBER_OF_INVOCATIONS_VALUE)).save(timetable);
    }

    @Test
    void shouldThrowEntityNotFoundExceptionWhenCantGetByTimetableId() {
        String message = format(ENTITY_NOT_FOUND, TIMETABLE);
        doThrow(new EntityNotFoundException(message)).when(mockTimetableRepository).findById(ID_5_VALUE);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> mockTimetableRepository.findById(ID_5_VALUE));
        String actual = exception.getMessage();

        assertEquals(message, actual);
    }
}
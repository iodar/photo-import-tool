package model.impl;

import model.ImageFile;
import model.core.ImageFileRepository;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.List;

import static java.util.Arrays.asList;
import static org.hamcrest.Matchers.*;
import static org.junit.Assert.*;

public class ImageFileRepositoryImplTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void emptyRepo_ShouldReturnEmpty() {
        // Assign
        final ImageFileRepository imageFileRepository = new ImageFileRepositoryImpl();

        // Act
        final Boolean isEmpty = imageFileRepository.isEmpty();

        // Act and Assert
        assertThat(isEmpty, is(true));
    }

    @Test
    public void emptyRepo_CallingGetElementAt_ShouldReturnNull() {
        // Assign
        final ImageFileRepository imageFileRepository = new ImageFileRepositoryImpl();

        // Act
        final ImageFile imageFile = imageFileRepository.getElementAt(0);

        // Act
        assertThat(imageFile, is(nullValue()));
    }

    @Test
    public void emptyRepo_RemovingElement_ShouldNotThrowException() {
        // Assign
        final ImageFileRepository imageFileRepository = new ImageFileRepositoryImpl();

        // Act
        imageFileRepository.deleteElementAt(0);
    }

    @Test
    public void emptyRepo_ClearingList_ShouldNotThrowException() {
        // Assing
        final ImageFileRepository imageFileRepository = new ImageFileRepositoryImpl();

        // Act
        imageFileRepository.clear();
    }

    @Test
    public void emptyRepo_GetAllElements_ShouldReturnEmptyList() {
        // Assign
        final ImageFileRepository imageFileRepository = new ImageFileRepositoryImpl();

        // Act
        final List<ImageFile> imageFiles = imageFileRepository.getAllElements();

        // Assert
        assertThat(imageFiles, is(empty()));
    }

    @Test
    public void afterAddingOneElementToRepo_CallingIsEmpty_ShouldReturnFalse() {
        // Assing
        final ImageFileRepository imageFileRepository = new ImageFileRepositoryImpl();
        final ImageFile imageFile = new ImageFile();

        // Act
        imageFileRepository.add(imageFile);
        final Boolean actualIsEmpty = imageFileRepository.isEmpty();

        // Assert
        assertThat(actualIsEmpty, is(not(true)));
    }

    @Test
    public void repoWithOneElement_AfterCallingRemoveElement_ShouldBeEmptyRepo() {
        // Assign
        final ImageFileRepository imageFileRepository = new ImageFileRepositoryImpl();
        final ImageFile imageFile = new ImageFile();

        // Act
        imageFileRepository.add(imageFile);
        imageFileRepository.deleteElementAt(0);

        // Assert
        assertThat(imageFileRepository.getAllElements().size(), is(0));
    }

    @Test
    public void repoWithTwoElements_AfterCallingClear_ShouldBeEmptyRepo() {
        // Assign
        final ImageFileRepository imageFileRepository = new ImageFileRepositoryImpl();
        final ImageFile imageFile = new ImageFile();
        final ImageFile imageFile1 = new ImageFile();

        // Act
        imageFileRepository.add(imageFile);
        imageFileRepository.add(imageFile1);
        imageFileRepository.clear();

        // Assert
        assertThat(imageFileRepository.isEmpty(), is(true));
    }

    @Test
    public void repoWithTwoElement_UponCallingGetAllElements_ShouldReturnListOfAllElements() {
        // Assign
        final ImageFileRepository imageFileRepository = new ImageFileRepositoryImpl();
        final ImageFile imageFile1 = new ImageFile();
        final ImageFile imageFile2 = new ImageFile();
        final List<ImageFile> imageFiles = asList(imageFile1, imageFile2);

        // Act
        imageFiles.stream().forEach(imageFile -> imageFileRepository.add(imageFile));

        // Assert
        assertThat(imageFileRepository.getAllElements(), is(equalTo(imageFiles)));

    }

    @Test
    public void repoWithOneElement_UponCallingGetElementAt_ShouldReturnElement() {
        // Assign
        final ImageFileRepository imageFileRepository = new ImageFileRepositoryImpl();
        final ImageFile imageFile = new ImageFile();

        // Act
        imageFileRepository.add(imageFile);

        // Assert
        assertThat(imageFileRepository.getElementAt(0), is(equalTo(imageFile)));
    }
}
package model.impl;

import model.ImageFile;
import model.core.ImageFileRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ImageFileRepositoryImpl implements ImageFileRepository {

    List<ImageFile> imageFiles = new ArrayList<>();

    @Override
    public void add(final ImageFile imageFile) {
        if (imageFile != null) {
            imageFiles.add(imageFile);
        }
    }

    @Override
    public ImageFile getElementAt(final Integer index) {
        try {
            return imageFiles.get(index);
        } catch (IndexOutOfBoundsException e) {

        }
        return null;
    }

    @Override
    public void deleteElementAt(final int index) {
        try {
            imageFiles.remove(index);
        } catch (NullPointerException | IndexOutOfBoundsException e) {

        }

    }

    @Override
    public void clear() {
        imageFiles.clear();
    }

    @Override
    public List<ImageFile> getAllElements() {
        return Collections.unmodifiableList(imageFiles);
    }

    @Override
    public Boolean isEmpty() {
        return imageFiles.isEmpty();
    }
}

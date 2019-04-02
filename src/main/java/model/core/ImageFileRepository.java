package model.core;

import model.ImageFile;

import java.util.List;

public interface ImageFileRepository {

    public void add(final ImageFile imageFile);

    public ImageFile getElementAt(final Integer index);

    public void deleteElementAt(final int index);

    public void clear();

    public List<ImageFile> getAllElements();

    public Boolean isEmpty();
}

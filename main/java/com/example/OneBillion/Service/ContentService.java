package com.example.OneBillion.Service;

import com.example.OneBillion.Model.Content;
import com.example.OneBillion.Repository.ContentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContentService {

    @Autowired
    private ContentRepository contentRepository;

    public List<Content> getAllContent() {
        return contentRepository.findAll();
    }

    public void saveContent(Content content) {
        contentRepository.save(content);
    }

    public Content getContentById(Long id) {
        return contentRepository.findById(id).orElseThrow(() -> new RuntimeException("Content not found"));
    }

    public void updateContent(Long id, Content updatedContent) {
        Content content = getContentById(id);
        content.setTitle(updatedContent.getTitle());
        content.setBody(updatedContent.getBody());
        contentRepository.save(content);
    }

    public void deleteContentById(Long id) {
        contentRepository.deleteById(id);
    }
}

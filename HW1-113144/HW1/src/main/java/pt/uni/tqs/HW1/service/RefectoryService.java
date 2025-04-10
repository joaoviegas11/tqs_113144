package pt.uni.tqs.HW1.service;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pt.uni.tqs.HW1.model.Refectory;
import pt.uni.tqs.HW1.repository.RefectoryRepository;

@Service
public class RefectoryService {

    private static final Logger logger = LoggerFactory.getLogger(RefectoryService.class);

    @Autowired
    private RefectoryRepository refectoryRepository;

    public List<Refectory> getAllRefectorys() {
        logger.info("Fetching all refectories.");
        List<Refectory> refectories = refectoryRepository.findAll();
        logger.debug("Refectories retrieved: {}", refectories);
        return refectories;
    }

    public Refectory getRefectoryById(Long id) {
        logger.info("Fetching refectory with ID: {}", id);
        return refectoryRepository.findById(id)
                .orElseThrow(() -> {
                    logger.error("Refectory with ID {} not found.", id);
                    return new IllegalArgumentException("Refectory with ID " + id + " not found.");
                });
    }

    
}

package com.example.spotter.service;

import java.util.Optional;

import com.example.spotter.dto.event.SpaceUpdatedEvent;
import com.example.spotter.dto.request.UpdateRequest;
import com.example.spotter.dto.response.SpaceResponse;
import com.example.spotter.model.Space;
import com.example.spotter.repository.SpaceRepository;

public class SpotterService {
	private final SpaceRepository _repository;
	private SpaceProducerService spaceEventProducer;
	
	public SpotterService(SpaceRepository repository, SpaceProducerService spaceEventProducer) {
		_repository = repository;
		this.spaceEventProducer = spaceEventProducer;
	}
	
	/**
	 * Returns whether a space is occupied.
	 * @param spaceId Which space to determine the occupancy of.
	 * @return Whether space {@value spaceId} is taken, or `empty` if the space does not exist.
	 */
    public Optional<Boolean> isOccupied(int spaceId) {
    	return _repository.findById(spaceId).map((space) -> space.isOccupied());
    }
    
    /**
     * Responds to a sensor activation for a spot. Does nothing if the spot does not exist.
     * @param spaceId
     */
    public void handleSensorActivation(int spaceId) {
    	_repository.findById(spaceId).ifPresent((space) -> space.setOccupied(!space.isOccupied()));
    }
    
    public SpaceResponse getSpace(Long spaceId) {
    	Space ret = _repository.findById(spaceId).orElseThrow(() -> new RuntimeException("Space not found"));
    	
    	return new SpaceResponse(ret);
    }
    
    public void update(UpdateRequest request, Long spaceId) {
    	Space ret = _repository.findById(spaceId).orElseThrow(() -> new RuntimeException("Space not found"));
    	
    	if (request.getMaxParkingMinutes() != null) {
    		ret.setMaxParkingMinutes(request.getMaxParkingMinutes());
    	}
    	if (request.getDisabilityPermitRequired() != null) {
    		ret.setDisabilityPermitRequired(request.getDisabilityPermitRequired());
    	}
    	if (request.getIsOccupied() != null) {
    		ret.setOccupied(request.getIsOccupied());
    	}
    	
    	_repository.save(ret);
    	// Raise event
    	SpaceUpdatedEvent event = new SpaceUpdatedEvent(ret);
    	spaceEventProducer.publishSpaceUpdatedEvent(event);
    }
}

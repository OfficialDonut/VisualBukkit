package com.gmail.visualbukkit.project;

import com.gmail.visualbukkit.reflection.ClassRegistry;
import org.eclipse.aether.repository.RemoteRepository;
import org.json.JSONObject;

public class MavenRepositoryModule extends MavenModule {

    private final RemoteRepository repository;

    public MavenRepositoryModule(RemoteRepository repository) {
        super(repository.toString(), String.format("%s:%s", repository.getId(), repository.getUrl()));
        this.repository = repository;
    }

    @Override
    public void enable() {
        ClassRegistry.register(repository);
    }

    @Override
    public JSONObject serialize() {
        JSONObject json = new JSONObject();
        json.put("id", repository.getId());
        json.put("url", repository.getUrl());
        return json;
    }

    public static MavenRepositoryModule deserialize(JSONObject json) {
        return new MavenRepositoryModule(new RemoteRepository.Builder(json.getString("id"), "default", json.getString("url")).build());
    }

    public RemoteRepository getRepository() {
        return repository;
    }
}

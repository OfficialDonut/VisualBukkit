package com.gmail.visualbukkit.project.maven;

import com.gmail.visualbukkit.project.BuildInfo;
import com.gmail.visualbukkit.reflection.ClassRegistry;
import org.eclipse.aether.repository.RemoteRepository;
import org.json.JSONObject;

public class MavenRepositoryModule extends MavenModule {

    private final RemoteRepository repository;

    public MavenRepositoryModule(RemoteRepository repository, boolean userDefined) {
        super(repository.toString(), String.format("Repo: %s (%s)", repository.getId(), repository.getUrl()), userDefined);
        this.repository = repository;
    }

    @Override
    public void enable() {
        if (isUserDefined()) {
            ClassRegistry.register(repository);
        }
    }

    @Override
    public void prepareBuild(BuildInfo buildInfo) {
        buildInfo.addMavenRepository(repository);
    }

    @Override
    public JSONObject serialize() {
        JSONObject json = new JSONObject();
        json.put("id", repository.getId());
        json.put("url", repository.getUrl());
        return json;
    }

    public static MavenRepositoryModule deserialize(JSONObject json) {
        return new MavenRepositoryModule(new RemoteRepository.Builder(json.getString("id"), "default", json.getString("url")).build(), true);
    }

    public RemoteRepository getRepository() {
        return repository;
    }
}

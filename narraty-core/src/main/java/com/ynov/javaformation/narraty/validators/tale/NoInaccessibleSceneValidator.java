package com.ynov.javaformation.narraty.validators.tale;

import com.ynov.javaformation.narraty.models.*;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class NoInaccessibleSceneValidator implements TaleValidator {

    public Optional<TaleErrors> validate(Tale tale) {
        var start = tale.scenes.stream().filter(s -> s.status == SceneStatus.Start).findFirst();
        if (start.isEmpty()) return Optional.of(TaleErrors.InaccessibleScene);

        Set<UUID> reachable = new HashSet<>();
        Queue<Scene> queue = new LinkedList<>();
        Map<UUID, Scene> sceneMap = tale.scenes.stream().collect(Collectors.toMap(s -> s.id, s -> s));

        reachable.add(start.get().id);
        queue.add(start.get());

        while (!queue.isEmpty()) {
            Scene current = queue.poll();
            for (Choice c : current.choices) {
                if (c.nextSceneId == null || reachable.contains(c.nextSceneId)) continue;
                Scene next = sceneMap.get(c.nextSceneId);
                if (next == null) continue;
                reachable.add(next.id);
                queue.add(next);
            }
        }

        boolean allReachable = tale.scenes.stream().allMatch(s -> reachable.contains(s.id));
        return allReachable ? Optional.empty() : Optional.of(TaleErrors.InaccessibleScene);
    }
}
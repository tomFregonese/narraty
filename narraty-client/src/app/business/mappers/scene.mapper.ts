import {Injectable} from '@angular/core';
import {EditScene, ReadScene} from '../models/scene.model';
import {EditSceneDtoIn, ReadSceneDtoIn} from '../dtos/scene.dto';
import {ChoiceMapper} from './choice.mapper';

@Injectable({providedIn: 'root'})
export class SceneMapper {

    constructor(private choiceMapper: ChoiceMapper) {}

    public mapReadSceneDtoInToReadScene(sceneDto: ReadSceneDtoIn): ReadScene {
        return {
            title: sceneDto.ttl,
            text: sceneDto.txt,
            choices: sceneDto.chcs.map(choice => this.choiceMapper.mapReadChoiceDtoInToReadChoice(choice))};
    }

    public mapEditSceneDtoInToEditScene(sceneDto: EditSceneDtoIn): EditScene {
        return {
            id: sceneDto.id,
            title: sceneDto.ttl,
            text: sceneDto.txt,
            status: sceneDto.stts,
            choices: sceneDto.chcs ? sceneDto.chcs.map(choice => this.choiceMapper.mapEditChoiceDtoInToEditChoice(choice)) : []
        };
    }

}

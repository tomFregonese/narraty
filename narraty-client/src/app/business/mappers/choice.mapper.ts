import {Injectable} from '@angular/core';
import {EditChoiceDtoIn, ReadChoiceDtoIn} from '../dtos/choice.dto';
import {EditChoice, ReadChoice} from '../models/choice.model';

@Injectable({providedIn: 'root'})
export class ChoiceMapper {

    public mapReadChoiceDtoInToReadChoice(choiceDto: ReadChoiceDtoIn): ReadChoice {
        return {
            id: choiceDto.id,
            text: choiceDto.txt,
        };
    }

    public mapEditChoiceDtoInToEditChoice(choiceDto: EditChoiceDtoIn): EditChoice {
        return {
            id: choiceDto.id,
            text: choiceDto.txt,
            nextSceneId: choiceDto.nxtScnId,
        };
    }

}

import {Component} from '@angular/core';
import { MainBlockComponent } from '../../components/main-block/main-block.component';
import { EditorSceneComponent } from '../../components/editor/editor-scene/editor-scene.component';
import { EditorChoiceComponent } from '../../components/editor/editor-choice/editor-choice.component';
import { EditorNavComponent } from '../../components/editor/editor-nav/editor-nav.component';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { EditorTaleComponent } from '../../components/editor/editor-tale/editor-tale.component';
import {EditTale} from '../../business/models/tale.model';
import {EditStoryService} from '../../business/services/story.service';
import {EditChoice} from '../../business/models/choice.model';
import {EditScene} from '../../business/models/scene.model';
import {ActivatedRoute, Router} from '@angular/router';


export interface Tale {
    id: string;
    title: string;
    description: string;
}

export interface Scene {
    id: string;
    text: string;
    result: number;
    choiceIds: string[];
}

export interface Choice {
    id: string;
    text: string;
    targetSceneId: string | null;
}

@Component({
    selector: 'app-editor',
    standalone: true,
    imports: [
        MainBlockComponent,
        EditorSceneComponent,
        EditorChoiceComponent,
        EditorNavComponent,
        EditorTaleComponent,
        CommonModule,
        FormsModule,
    ],
    templateUrl: './editor.page.html',
    styleUrl: './editor.page.scss',
})
export class EditorPage {

    protected taleId: string;
    protected tale: Tale = { id: '', title: '', description: '' };
    protected scenes: Scene[] = [];
    protected choices: Choice[] = [];

    constructor(private editStoryService: EditStoryService, private route: ActivatedRoute, private router: Router) {
        this.taleId = this.route.snapshot.paramMap.get('taleId') || '';
        this.editStoryService.getEditTaleInfo(this.taleId).subscribe((tale: EditTale) => {
            console.log(tale);
            this.fillTaleData(tale);
        });
    }

    private fillTaleData(tale: EditTale) {
        this.tale = {
            id: tale.id,
            title: tale.title,
            description: tale.description,
        };
        this.scenes = tale.scenes.map((scene) => ({
            id: scene.id,
            text: scene.text,
            result: scene.status,
            choiceIds: scene.choices.map((choice) => choice.id),
        }));
        this.choices = tale.scenes.flatMap((scene) =>
            scene.choices.map((choice) => ({
                id: choice.id,
                text: choice.text,
                targetSceneId: choice.nextSceneId,
            }))
        );
    }

    showPopup = false;

    onOpenPopup() {
        this.showPopup = true;
    }

    currentSceneId: string = '';

    get sceneIds(): string[] {
        return this.scenes.map((s) => s.id);
    }

    get currentScene(): Scene | undefined {
        return this.scenes.find((scene) => scene.id === this.currentSceneId);
    }

    get currentChoices(): Choice[] {
        const ids = this.currentScene?.choiceIds || [];
        return this.choices.filter((choice) => ids.includes(choice.id));
    }

    onSceneChange(newSceneId: string) {
        this.currentSceneId = newSceneId;
    }

    addScene() {
        this.editStoryService.createScene(this.taleId).subscribe((newScene: EditScene) => {
            const scene: Scene = {
                id: newScene.id,
                text: newScene.text,
                result: newScene.status,
                choiceIds: newScene.choices.map((choice) => choice.id),
            }
            this.scenes.push(scene);
        });
    }

    addChoice() {
        this.editStoryService.createChoice(this.currentSceneId).subscribe((newChoice: EditChoice) => {
            const choice: Choice = {
                id: newChoice.id,
                text: newChoice.text,
                targetSceneId: newChoice.nextSceneId,
            }
            this.choices.push(choice);
        });
    }

    onClosePopup() {
        this.showPopup = false;
    }

    publishTale() {

    }

    archiveTale() {

    }

    deleteTale() {
        this.editStoryService.deleteTale(this.taleId).subscribe(() => {
            this.router.navigate(['/tales-dashboard']);
        });
    }
}
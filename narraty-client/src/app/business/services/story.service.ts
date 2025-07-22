import { Injectable } from '@angular/core';
import {catchError, map, Observable, throwError} from 'rxjs';
import {AbstractService} from './abstract.service';
import {AuthService} from './auth.service';
import {HttpClient} from '@angular/common/http';
import {ReadTaleDtoIn} from '../dtos/tale.dto';
import {TaleMapper} from '../mappers/tale.mapper';
import {EditTale, ReadTale} from '../models/tale.model';
import {EditScene, ReadScene} from '../models/scene.model';
import {SceneMapper} from '../mappers/scene.mapper';
import {UpdateStatusResultDto} from '../dtos/UpdateStatusResultDto';
import {EditChoice} from '../models/choice.model';
import {ChoiceMapper} from '../mappers/choice.mapper';
import { SceneStatus } from '../enums/scene-status.enum';

@Injectable({
    providedIn: 'root'
})
class StoryService extends AbstractService {
    token: string | null;
    headers: any;

    constructor(httpClient: HttpClient, private authService: AuthService) {
        super(httpClient);
        this.token = this.authService.getTokenFromCookies();
        this.headers = { Authorization: `Bearer ${this.token}` }
    }
}

@Injectable({
    providedIn: 'root'
})
export class DisplayStoryService extends StoryService {

    constructor(httpClient: HttpClient, authService: AuthService, private taleMapper: TaleMapper) {
        super(httpClient, authService);
    }
    private displayStoryApiUrl = `${this.backBaseUrl}/public-story`;

    getAllTales(): Observable<ReadTale[]> {
        const url = `${this.displayStoryApiUrl}/all-tales`;

        return this.httpClient.get(url).pipe(
            map((response: any) => {
                return response.map((taleDto: ReadTaleDtoIn) => {
                    return this.taleMapper.mapReadTaleDtoInToReadTaleModel(taleDto);
                });
            }),
            catchError((error) => {
                return throwError(error);
            })
        );

    }

    getTaleById(taleId: string): Observable<ReadTale> {
        const url = `${this.displayStoryApiUrl}/tale/${taleId}`;

        return this.httpClient.get(url).pipe(
            map((response: any) => {
                return this.taleMapper.mapReadTaleDtoInToReadTaleModel(response);
            }),
            catchError((error) => {
                return throwError(error);
            })
        );

    }

    getRecentTalesByUser(): Observable<ReadTale[]> { // TODO : Not implemented in the backend yet
        const url = `${this.displayStoryApiUrl}/recent-tales`;
        const headers = this.headers

        return this.httpClient.get(url, { headers }).pipe(
            map((response: any) => {
                return response.map((taleDto: ReadTaleDtoIn) => {
                    return this.taleMapper.mapReadTaleDtoInToReadTaleModel(taleDto);
                });
            }),
            catchError((error) => {
                return throwError(error);
            })
        );

    }

}

@Injectable({
    providedIn: 'root'
})
export class PlayStoryService extends StoryService {

    constructor(httpClient: HttpClient, authService: AuthService, private sceneMapper: SceneMapper) {
        super(httpClient, authService);
    }
     private playStoryApiUrl = `${this.backBaseUrl}/play-story`;

    readStory(taleId: string): Observable<ReadScene> {
        const url = `${this.playStoryApiUrl}/scene-to-display/${taleId}`;
        const headers = this.headers

        return this.httpClient.get(url, { headers }).pipe(
            map((response: any) => {
                return this.sceneMapper.mapReadSceneDtoInToReadScene(response);
            }),
            catchError((error) => {
                return throwError(error);
            })
        );

    }

    makeAChoice(taleId: string, selectedChoiceId: string): Observable<ReadScene> {
        const url = `${this.playStoryApiUrl}/save-user-choice/${taleId}`;
        const headers = this.headers;
        const body = { choiceId: selectedChoiceId };

        return this.httpClient.post(url, body, { headers }).pipe(
            map((response: any) => {
                return this.sceneMapper.mapReadSceneDtoInToReadScene(response);
            }),
            catchError((error) => {
                return throwError(error);
            })
        );

    }

}

@Injectable({
    providedIn: 'root'
})
export class EditStoryService extends StoryService {

    constructor(httpClient: HttpClient,
                authService: AuthService,
                private readonly taleMapper: TaleMapper,
                private readonly sceneMapper: SceneMapper,
                private readonly choiceMapper: ChoiceMapper) {
        super(httpClient, authService);
    }

    private editStoryApiUrl = `${this.backBaseUrl}/edit-story`;

    createTale(): Observable<EditTale> {
        const url = `${this.editStoryApiUrl}/create-tale`;
        const headers = this.headers;

        return this.httpClient.post(url, null, { headers }).pipe(
            map((response: any) => {
                return this.taleMapper.mapEditTaleDtoInToEditTaleModel(response);
            }),
            catchError((error) => {
                return throwError(error);
            })
        );
    }

    getEditTaleInfo(taleId: string): Observable<EditTale> {
        const url = `${this.editStoryApiUrl}/tale/${taleId}/info`;
        const headers = this.headers

        return this.httpClient.get(url, { headers }).pipe(
            map((response: any) => {
                return this.taleMapper.mapEditTaleDtoInToEditTaleModel(response);
            }),
            catchError((error) => {
                return throwError(error);
            })
        );

    }

    editStoryTitle(taleId: string, newTitle: string): Observable<EditTale> {
        const url = `${this.editStoryApiUrl}/tale/${taleId}/title`;
        const body = { ttl: newTitle };
      
        return this.httpClient.put(url, body, { headers: this.headers }).pipe(
          map((response: any) => {
            return this.taleMapper.mapEditTaleDtoInToEditTaleModel(response);
          }),
          catchError((error) => {
            return throwError(error);
          })
        );
      }

      editStoryDescription(taleId: string, newDescription: string): Observable<EditTale> {
        const url = `${this.editStoryApiUrl}/tale/${taleId}/description`;
        const body = { dsc: newDescription };
        return this.httpClient.put(url, body, { headers: this.headers }).pipe(
            map((response: any) => {
                return this.taleMapper.mapEditTaleDtoInToEditTaleModel(response);
            }),
            catchError((error) => {
                return throwError(error);
            })
        );
    }

    editStoryStatus(taleId: string, newStatus: number): Observable<EditTale | string[]> { // TODO : Not tested yet
        // TODO : Cet appel peut s'avérer plus long que les autres => prévoir un loader pour faire patienter
        //  l'utilisateur.
        const url = `${this.editStoryApiUrl}/tale/${taleId}/status`;
        const headers = this.headers
        const body = { newStatus }

        return this.httpClient.put(url, body, { headers }).pipe(
            map((response: any) => {
                const result: UpdateStatusResultDto = response;
                if (result.errors) {
                    return result.errors;
                }
                return this.taleMapper.mapEditTaleDtoInToEditTaleModel(result.tale!);
            }),
            catchError((error) => {
                return throwError(error);
            })
        );

    }

    deleteTale(taleId: string): Observable<void> { // TODO : Not tested yet
        const url = `${this.editStoryApiUrl}/tale/${taleId}`;
        const headers = this.headers;

        return this.httpClient.delete<void>(url, { headers }).pipe(
            catchError((error) => {
                return throwError(error);
            })
        );
    }

    createScene(taleId: string): Observable<EditScene> { // TODO : Not tested yet
        const url = `${this.editStoryApiUrl}/tale/${taleId}/create-scene`;
        const headers = this.headers
        console.log('headers', headers)

        return this.httpClient.post(url, null, { headers }).pipe(
            map((response: any) => {
                return this.sceneMapper.mapEditSceneDtoInToEditScene(response);
            }),
            catchError((error) => {
                return throwError(error);
            })
        );

    }

    editSceneTitle(sceneId: string, newTitle: string): Observable<EditTale> { // TODO : Not tested yet
        const url = `${this.editStoryApiUrl}/scene/${sceneId}/title`;
        const headers = this.headers
        const body = { newTitle }

        return this.httpClient.put(url, body, { headers }).pipe(
            map((response: any) => {
                return this.taleMapper.mapEditTaleDtoInToEditTaleModel(response);
            }),
            catchError((error) => {
                return throwError(error);
            })
        );

    }

    editSceneText(sceneId: string, newText: string): Observable<EditTale> {
        const url = `${this.editStoryApiUrl}/scene/${sceneId}/text`;
        const headers = this.headers;
        const body = { text: newText }; 
    
        return this.httpClient.put(url, body, { headers }).pipe(
            map((response: any) => {
                return this.taleMapper.mapEditTaleDtoInToEditTaleModel(response);
            }),
            catchError((error) => {
                return throwError(error);
            })
        );
    }

    editSceneStatus(sceneId: string, newStatus: SceneStatus): Observable<EditTale> {
        const url = `${this.editStoryApiUrl}/scene/${sceneId}/status`;
        const headers = this.headers;
        const body = { status: newStatus };
    
        return this.httpClient.put(url, body, { headers }).pipe(
            map((response: any) => {
                return this.taleMapper.mapEditTaleDtoInToEditTaleModel(response);
            }),
            catchError((error) => {
                return throwError(error);
            })
        );
    }

    deleteScene(sceneId: string): Observable<void> { // TODO : Not tested yet
        const url = `${this.editStoryApiUrl}/scene/${sceneId}`;
        const headers = this.headers;

        return this.httpClient.delete<void>(url, { headers }).pipe(
            catchError((error) => {
                return throwError(error);
            })
        );
    }

    createChoice(sceneId: string): Observable<EditChoice> { // TODO : Not tested yet
        const url = `${this.editStoryApiUrl}/scene/${sceneId}/create-choice`;
        const headers = this.headers

        return this.httpClient.post(url, null, { headers }).pipe(
            map((response: any) => {
                return this.choiceMapper.mapEditChoiceDtoInToEditChoice(response);
            }),
            catchError((error) => {
                return throwError(error);
            })
        );

    }

    editChoiceText(choiceId: string, newText: string): Observable<EditTale> {
        const url = `${this.editStoryApiUrl}/choice/${choiceId}/text`;
        const headers = this.headers;
        const body = { text: newText };
    
        return this.httpClient.put(url, body, { headers }).pipe(
            map((response: any) => {
                return this.taleMapper.mapEditTaleDtoInToEditTaleModel(response);
            }),
            catchError((error) => {
                return throwError(error);
            })
        );
    }

    editChoiceTargetScene(choiceId: string, nextSceneId: string | null): Observable<EditTale> {
        const url = `${this.editStoryApiUrl}/choice/${choiceId}/next-scene`;
        const headers = this.headers;
        const body = { nextSceneId }; 
    
        return this.httpClient.put(url, body, { headers }).pipe(
            map((response: any) => {
                return this.taleMapper.mapEditTaleDtoInToEditTaleModel(response);
            }),
            catchError((error) => {
                return throwError(error);
            })
        );
    }

    deleteChoice(choiceId: string): Observable<void> { // TODO : Not tested yet
        const url = `${this.editStoryApiUrl}/choice/${choiceId}`;
        const headers = this.headers;

        return this.httpClient.delete<void>(url, { headers }).pipe(
            catchError((error) => {
                return throwError(error);
            })
        );
    }

    getAllMyTales(): Observable<EditTale[]> { // TODO : Not tested yet
        const url = `${this.editStoryApiUrl}/my-tales`;
        const headers = this.headers;

        return this.httpClient.get(url, { headers }).pipe(
            map((response: any) => {
                return response.map((taleDto: any) => {
                    return this.taleMapper.mapEditTaleDtoInToEditTaleModel(taleDto);
                });
            }),
            catchError((error) => {
                return throwError(error);
            })
        );
    }

}
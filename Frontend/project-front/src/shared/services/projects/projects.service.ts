import { httpClient } from '../http-client';
import { CreateProjectInput, Project, UpdateProjectInput } from './types';

export class ProjectService {
  private static readonly BASE_PATH = '/projects';

  static async getProjects() {
    return httpClient.get<Project[]>(this.BASE_PATH);
  }

  static async getProject(id: number): Promise<Project> {
    const { data } = await httpClient.get<Project>(`${this.BASE_PATH}/${id}`);
    return data;
  }

  static async createProject(input: CreateProjectInput): Promise<Project> {
    const { data } = await httpClient.post<Project>(this.BASE_PATH, input);
    return data;
  }

  static async updateProject(id: number, input: UpdateProjectInput): Promise<Project> {
    const { data } = await httpClient.patch<Project>(`${this.BASE_PATH}/${id}`, input);
    return data;
  }

  static async deleteProject(id: number): Promise<void> {
    await httpClient.delete(`${this.BASE_PATH}/${id}`);
  }
}

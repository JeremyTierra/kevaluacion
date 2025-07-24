export interface Project {
  id: number;
  name: string;
  description: string;
  createdAt: string;
}

export interface CreateProjectInput {
  name: string;
  description: string;
}

export type UpdateProjectInput = Partial<CreateProjectInput>;

export type ProjectsResponse = Project[]

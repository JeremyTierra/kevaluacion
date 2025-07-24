'use client';

import { useEffect, useState } from 'react';
import { Form } from '@/shared/form';
import { useZodForm } from '@/shared/form/hook/use-zod-form';
import { AdvancedInputField } from '@/components/molecules/input/advanced-input';
import { SelectField } from '@/components/molecules/select/select-field';
import { DatePickerField } from '@/components/molecules/date-picker/date-picker-field';
import { Task, TaskStatus } from '@/shared/services/tasks/types';
import { TaskService } from '@/shared/services/tasks/tasks.service';
import { Button } from '@/components/atoms/button';
import { UserService } from '@/shared/services/users';
import { ProjectService } from '@/shared/services/projects/projects.service';
import { taskSchema, type TaskFormData } from '../validators/task.schema';


interface TaskFormProps {
  task?: Task;
  onSuccess?: () => void;
}

const STATUS_OPTIONS: { label: string; value: TaskStatus }[] = [
  { label: 'Done', value: 'DONE' },
  { label: 'In Progress', value: 'IN_PROGRESS' },
  { label: 'Pending', value: 'PENDING' },
];

export function TaskForm({ task, onSuccess }: TaskFormProps) {
  const [users, setUsers] = useState<Array<{ label: string; value: number }>>([]);
  const [projects, setProjects] = useState<Array<{ label: string; value: number }>>([]);

  const form = useZodForm(taskSchema, {
    defaultValues: {
      title: task?.title || '',
      description: task?.description || '',
      status: task?.status || 'PENDING',
      assignedToUserId: task?.assignedToUserId || 0,
      projectId: task?.projectId || 0,
      dueDate: task?.dueDate || new Date().toISOString(),
    },
  });

  useEffect(() => {
    const fetchData = async () => {
      try {
        const [usersResponse, projectsResponse] = await Promise.all([
          UserService.getUsers(),
          ProjectService.getProjects()
        ]);

        const formattedUsers = usersResponse.data.map(user => ({
          label: user.username,
          value: user.id
        }));

        const formattedProjects = projectsResponse.data.map(project => ({
          label: project.name,
          value: project.id
        }));

        console.log('🔍 Raw users response:', usersResponse);
        console.log('🔍 Raw projects response:', projectsResponse);
        console.log('🔍 Formatted users:', formattedUsers);
        console.log('🔍 Formatted projects:', formattedProjects);

        setUsers(formattedUsers);
        setProjects(formattedProjects);
      } catch (error) {
        console.error('Error fetching form data:', error);
      }
    };

    fetchData();
  }, []);

  const onSubmit = async (data: TaskFormData) => {
    try {
      if (task) {
        await TaskService.updateTask(task.id, data);
      } else {
        await TaskService.createTask(data);
      }
      onSuccess?.();
    } catch (error) {
      console.error('Error saving task:', error);
    }
  };

  return (
    <Form onSubmit={onSubmit} className="space-y-4" methods={form}>
      <AdvancedInputField
        name="title"
        label="Título"
        placeholder="Ingrese el título de la tarea"
      />
      
      <AdvancedInputField
        name="description"
        label="Descripción"
        placeholder="Ingrese la descripción de la tarea"
      />

      <SelectField
        name="status"
        label="Estado"
        options={STATUS_OPTIONS}
        placeholder="Seleccione el estado"
      />

      <SelectField
        name="assignedToUserId"
        label="Asignado a"
        options={users}
        placeholder="Seleccione un usuario"
        valueType="number"
      />

      <SelectField
        name="projectId"
        label="Proyecto"
        options={projects}
        placeholder="Seleccione un proyecto"
        valueType="number"
      />

      <DatePickerField
        name="dueDate"
        label="Fecha límite"
      />

      <div className="flex justify-end gap-2">
        <Button type="submit">
          {task ? 'Update' : 'Create'} Task
        </Button>
      </div>
    </Form>
  );
}

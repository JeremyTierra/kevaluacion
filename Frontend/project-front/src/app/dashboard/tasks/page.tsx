'use client';

import { useState, useEffect, useCallback } from 'react';
import { Task } from '@/shared/services/tasks/types';
import { TaskService } from '@/shared/services/tasks/tasks.service';
import { DataTable } from '@/components/molecules/data-table/data-table';
import { Button } from '@/components/atoms/button';
import {
  Sheet,
  SheetContent,
  SheetHeader,
  SheetTitle,
  SheetTrigger,
} from '@/components/atoms/sheet';
import { Edit, Plus, Trash } from 'lucide-react';
import { format } from 'date-fns';
import { es } from 'date-fns/locale';
import { ColumnDef } from '@tanstack/react-table';
import { TaskForm } from './components/TaskForm';
import { useAuth } from '@/shared/hooks/useAuth';
import { useRole } from '@/shared/hooks/useRole';

export default function TasksPage() {
  const { user } = useAuth();
  const { isAdmin } = useRole();
  const [selectedTask, setSelectedTask] = useState<Task | undefined>();

  const fetchUsersAndProjects = useCallback(async () => {
    try {
    } catch (error) {
      console.error('Error fetching users and projects:', error);
    }
  }, []);

  useEffect(() => {
    fetchUsersAndProjects();
  }, [fetchUsersAndProjects]);

  const columns: ColumnDef<Task>[] = [
    {
      accessorKey: 'title',
      header: 'Título',
    },
    {
      accessorKey: 'description',
      header: 'Descripción',
    },
    {
      accessorKey: 'status',
      header: 'Estado',
      cell: ({ row }) => {
        const statusMap = {
          'DONE': 'Completado',
          'IN_PROGRESS': 'En Progreso',
          'PENDING': 'Pendiente'
        };
        return statusMap[row.original.status] || row.original.status;
      },
    },
    {
      accessorKey: 'dueDate',
      header: 'Fecha límite',
      cell: ({ row }) => format(new Date(row.original.dueDate), 'PPP', { locale: es }),
    },
    {
      id: 'actions',
      header: 'Acciones',
      cell: ({ row }) => {
        const task = row.original;

        return (
          <div className="flex items-center gap-2">
            <Button
              variant="ghost"
              size="icon"
              onClick={() => setSelectedTask(task)}
            >
              <Edit className="h-4 w-4" />
            </Button>
            <Button
              variant="ghost"
              size="icon"
              onClick={() => handleDelete(task.id)}
            >
              <Trash className="h-4 w-4" />
            </Button>
          </div>
        );
      },
    },
  ];

  const [tasks, setTasks] = useState<Task[]>([]);

  const fetchTasks = useCallback(async () => {
    try {
      console.log('🔍 User object:', user);
      console.log('🔍 Fetching all tasks...');
      const response = await TaskService.getTasks();
      console.log('🔍 Tasks response:', response);
      
      setTasks(response.data);
      console.log('🔍 Tasks set to state:', response.data);
    } catch (error) {
      console.error('❌ Error fetching tasks:', error);
    }
  }, []);

  useEffect(() => {
    fetchTasks();
  }, [fetchTasks]);

  const handleDelete = async (taskId: number) => {
    try {
      await TaskService.deleteTask(taskId);
      await fetchTasks();
    } catch (error) {
      console.error('Error deleting task:', error);
    }
  };

  return (
    <div className="min-h-screen">
      <main className="container mx-auto px-4 py-8">
        <div className="flex justify-between items-center mb-6">
          <h2 className="text-xl font-semibold">Mis Tareas</h2>
          {isAdmin() && (
            <Sheet>
              <SheetTrigger asChild>
                <Button>
                  <Plus className="mr-2 h-4 w-4" />
                  Nueva tarea
                </Button>
              </SheetTrigger>
              <SheetContent>
                <SheetHeader>
                  <SheetTitle>{selectedTask ? 'Editar tarea' : 'Crear nueva tarea'}</SheetTitle>
                </SheetHeader>
                <div className="m-4">
                  <TaskForm
                    task={selectedTask}
                    onSuccess={() => {
                      setSelectedTask(undefined);
                      fetchTasks();
                    }}
                  />
                </div>
              </SheetContent>
            </Sheet>
          )}
        </div>

        <DataTable
          columns={columns}
          data={tasks}
        />
        
        
      </main>
    </div>
  );
}

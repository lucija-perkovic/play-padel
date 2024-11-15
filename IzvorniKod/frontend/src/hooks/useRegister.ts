import { useMutation } from "@tanstack/react-query";
import {User} from "@/models/user";
import {register} from "@/services/backend";

export const useRegister = () => {
  console.log("mutation");

  return useMutation({
    mutationFn: (user: User) => register(user),
  });
};
import { FC, useEffect } from "react";
import { TextField, Button, Typography, Container, Stack } from "@mui/material";
import { useForm, SubmitHandler, Controller } from "react-hook-form";
import { useLogin } from "@/hooks/useLogin";
import Link from "next/link";
import {router} from "next/client";

// Define the form data type
interface IFormInput {
  email: string;
  password: string;
}

const LoginPage: FC = () => {
  const {
    control,
    handleSubmit,
    formState: { errors },
  } = useForm<IFormInput>();

  const mutation = useLogin();

  const onSubmit: SubmitHandler<IFormInput> = (data) => {
    console.log(data);
    // Handle form submission (e.g., send data to API)
    mutation.mutate({
      email: data.email,
      password: data.password,
    });
  };

  useEffect(() => {
    if (mutation.isSuccess) {
      // Handle successful registration
      console.log("Login successful", mutation.data);
      router.push("/profile/me").then(r => console.log(r));
    }
  }, [mutation.data, mutation.error, mutation.isError, mutation.isSuccess]);

  return (
      <Container maxWidth="xs">
        <Stack alignItems="center" spacing={2}>
          <Typography variant="h4" gutterBottom>
            Login
          </Typography>

          <form onSubmit={handleSubmit(onSubmit)} style={{ width: "100%" }}>
            <Stack spacing={2}>
              <Controller
                  name="email"
                  control={control}
                  rules={{
                    required: "Email is required",
                  }}
                  render={({ field }) => (
                      <TextField
                          {...field}
                          label="Email"
                          variant="outlined"
                          fullWidth
                          margin="normal"
                          error={!!errors.email}
                          helperText={errors.email?.message}
                      />
                  )}
              />

              <Controller
                  name="password"
                  control={control}
                  rules={{
                    required: "Password is required",
                    minLength: {
                      value: 6,
                      message: "Password must be at least 6 characters",
                    },
                  }}
                  render={({ field }) => (
                      <TextField
                          {...field}
                          label="Password"
                          type="password"
                          variant="outlined"
                          fullWidth
                          margin="normal"
                          error={!!errors.password}
                          helperText={errors.password?.message}
                      />
                  )}
              />

              <Stack>
                <Button
                    type="submit"
                    variant="contained"
                    color="primary"
                    sx={{ width: "100%" }}
                    disabled={Object.keys(errors).length > 0}
                >
                  Register
                </Button>
              </Stack>
              <Stack>
                <Typography variant="body2" align="center">
                  {mutation.isError && (
                      <Typography
                          color="error"
                          variant="body2"
                          sx={{ marginBottom: 2 }}
                      >
                        Email or password is incorrect. Please try again.
                      </Typography>
                  )}
                </Typography>
              </Stack>
              <Stack>
                <Typography variant="body2" align="center">
                  Don&#39;t have an account?
                  <Link href="/register">Register</Link>
                </Typography>
              </Stack>
            </Stack>
          </form>
        </Stack>
      </Container>
  );
};

export default LoginPage;
